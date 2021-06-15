import React from "react";
import Box from "@material-ui/core/Box";

import Button from "@material-ui/core/Button";
import Card from "@material-ui/core/Card";
import Typography from "@material-ui/core/Typography";
import Grid from "@material-ui/core/Grid";

import CardContent from "@material-ui/core/CardContent";

import OutlinedInput from "@material-ui/core/OutlinedInput";
import InputAdornment from "@material-ui/core/InputAdornment";
import IconButton from "@material-ui/core/IconButton";
import InputLabel from "@material-ui/core/InputLabel";
import FormControl from "@material-ui/core/FormControl";

import Visibility from "@material-ui/icons/Visibility";
import VisibilityOff from "@material-ui/icons/VisibilityOff";

import MuiAlert from "@material-ui/lab/Alert";

import { Link, useHistory } from "react-router-dom";

import Logo from "../res/logo_white.png";

import Backdrop from "@material-ui/core/Backdrop";
import CircularProgress from "@material-ui/core/CircularProgress";

export default class Login extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      email: "",
      password: "",
      showPassword: false,
      wrongPassword: false,
      accountConfirmed: this.checkVerifica(),
      loadingOpen: false,
    };
  }

  handleChange = (e) => {
    this.setState({ [e.target.id]: e.target.value });
  };

  handleClickShowPassword = () => {
    this.setState({ showPassword: !this.state.showPassword });
  };

  handleMouseDownPassword = (e) => {
    e.preventDefault();
  };
  handleCloseLoading = () => {
    this.setState({ loadingOpen: false });
  };
  handleToggleLoading = () => {
    this.setState({ loadingOpen: !this.state.open });
  };

  checkVerifica() {
    let hash = this.props.match.params.hash;
    let email = this.props.match.params.email;

    if (hash != null && email != null) {
      const conferma = {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          email: email,
          hash: hash,
        }),
      };

      fetch("/api/confermaRegistrazione", conferma)
        .then((res) => res.json())
        .then((data) => {
          if (data.state === "success") {
            //already in login page
            //let history = this.props.history;
            //history.push("/login");
            this.setState({ accountConfirmed: true });
          } else {
            console.log("Errore conferma");
          }
          this.handleCloseLoading();
        })
        .catch((err) => console.log(err));
    }

    return hash != null && email != null;
  }

  callAPI = (e) => {
    e.preventDefault();
    console.log(this.state);
    this.handleToggleLoading();
    const requestOptions = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        email: this.state.email,
        password: this.state.password,
      }),
    };
    fetch("/api/login", requestOptions)
      .then((res) => res.json())
      .then((data) => {
        if (data.state === "success") {
          let tipoUtente = parseInt(data.tipoUtente);
          localStorage.setItem("id", data.id);
          localStorage.setItem("nome", data.nome);
          localStorage.setItem("cognome", data.cognome);
          localStorage.setItem("email", data.email);
          localStorage.setItem("tipoUtente", tipoUtente);
          let history = this.props.history;
          switch (tipoUtente) {
            case 0:
              history.push("/bacheca");
              break;
            case 1:
              history.push("/segnalazioniDaApprovare");
              break;
            case 2:
              history.push("/segnalazioniApprovate");
              break;
            case 3:
              history.push("/homepageAdmin");
              break;
          }
        } else {
          this.setState({ wrongPassword: true, email: "", password: "" });
        }
        this.handleCloseLoading();
      })
      .catch((err) => console.log(err));
  };

  render() {
    return (
      <Box style={{ background: "linear-gradient(#7274ed, #c76d5a)" }}>
        <Grid
          container
          spacing={0}
          direction="column"
          alignItems="center"
          justify="center"
          style={{ minHeight: "100vh", overflow: "hidden" }}
        >
          <Grid item>
            <img src={Logo} width="150" />
            <br />
            <br />
          </Grid>
          <Grid item>
            <Typography variant="h6" style={{ color: "white" }}>
              Effettua l'accesso alla tua comunità
            </Typography>
            <br />
          </Grid>

          {this.state.accountConfirmed ? (
            <Grid item>
              <MuiAlert
                elevation={6}
                variant="filled"
                severity="success"
                fullWidth
              >
                Il tuo account è stato confermato con successo.
                <br /> Adesso puoi effettuare l'accesso
              </MuiAlert>
              <br />
            </Grid>
          ) : null}

          {this.state.wrongPassword ? (
            <Grid item>
              <MuiAlert
                elevation={6}
                variant="filled"
                severity="error"
                fullWidth
              >
                La password inserita è errata! Riprova
              </MuiAlert>
              <br />
            </Grid>
          ) : null}

          <Grid item>
            <Card>
              <CardContent>
                <form onSubmit={this.callAPI}>
                  <FormControl variant="outlined" fullWidth>
                    <InputLabel htmlFor="email">E-Mail</InputLabel>
                    <OutlinedInput
                      id="email"
                      type="email"
                      value={this.state.email}
                      onChange={this.handleChange}
                      labelWidth={50}
                      required
                    />
                  </FormControl>
                  <br />
                  <br />
                  <FormControl variant="outlined" fullWidth>
                    <InputLabel htmlFor="password">Password</InputLabel>
                    <OutlinedInput
                      id="password"
                      type={this.state.showPassword ? "text" : "password"}
                      value={this.state.password}
                      onChange={this.handleChange}
                      endAdornment={
                        <InputAdornment position="end">
                          <IconButton
                            aria-label="toggle password visibility"
                            onClick={this.handleClickShowPassword}
                            onMouseDown={this.handleMouseDownPassword}
                            edge="end"
                          >
                            {this.state.showPassword ? (
                              <Visibility />
                            ) : (
                              <VisibilityOff />
                            )}
                          </IconButton>
                        </InputAdornment>
                      }
                      required
                      labelWidth={70}
                    />

                    <Button
                      type="submit"
                      variant="contained"
                      size="large"
                      color="primary"
                      style={{ margin: 0, marginTop: "15px" }}
                    >
                      Accedi
                    </Button>
                  </FormControl>
                </form>
              </CardContent>
            </Card>
          </Grid>
          <Grid item>
            <br />
            <span style={{ color: "white" }}>oppure</span>
          </Grid>
          <Grid item>
            <Button
              variant="contained"
              component={Link}
              to="/registrazione"
              size="large"
              color="primary"
              style={{ margin: 0, marginTop: "15px" }}
            >
              Registrati adesso
            </Button>
          </Grid>
        </Grid>
        <Backdrop
          open={this.state.loadingOpen}
          style={{ zIndex: 100, color: "#fff" }}
        >
          <CircularProgress color="inherit" />
        </Backdrop>
      </Box>
    );
  }
}
