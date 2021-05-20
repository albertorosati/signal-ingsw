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

export default function Login() {
  const [values, setValues] = React.useState({
    email: "",
    password: "",
    showPassword: false,
    wrongPassword: false,
  });

  const history = useHistory();

  const handleChange = (prop) => (event) => {
    setValues({ ...values, [prop]: event.target.value });
  };

  const handleClickShowPassword = () => {
    setValues({ ...values, showPassword: !values.showPassword });
  };

  const handleMouseDownPassword = (event) => {
    event.preventDefault();
  };

  const [loadingOpen, setOpenLoading] = React.useState(false);
  const handleCloseLoading = () => {
    setOpenLoading(false);
  };
  const handleToggleLoading = () => {
    setOpenLoading(!open);
  };

  const callAPI = (e) => {
    e.preventDefault();
    handleToggleLoading();
    const requestOptions = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email: values.email, password: values.password }),
    };
    fetch("/api/login", requestOptions)
      .then((res) => res.json())
      .then((data) => {
        if (data.state === "success") {
          localStorage.setItem("id", data.id);
          localStorage.setItem("nome", data.nome);
          localStorage.setItem("cognome", data.cognome);
          localStorage.setItem("email", data.email);
          localStorage.setItem("tipoUtente", data.tipoUtente);
          switch (data.tipoUtente) {
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
          setValues({ wrongPassword: true, email: "", password: "" });
        }
        handleCloseLoading();
      })
      .catch((err) => console.log(err));
  };

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

        {values.wrongPassword ? (
          <Grid item>
            <MuiAlert elevation={6} variant="filled" severity="error" fullWidth>
              La password inserita è errata! Riprova
            </MuiAlert>
            <br />
          </Grid>
        ) : null}

        <Grid item>
          <Card>
            <CardContent>
              <form onSubmit={callAPI}>
                <FormControl variant="outlined" fullWidth>
                  <InputLabel htmlFor="email">E-Mail</InputLabel>
                  <OutlinedInput
                    id="email"
                    type="email"
                    value={values.email}
                    onChange={handleChange("email")}
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
                    type={values.showPassword ? "text" : "password"}
                    value={values.password}
                    onChange={handleChange("password")}
                    endAdornment={
                      <InputAdornment position="end">
                        <IconButton
                          aria-label="toggle password visibility"
                          onClick={handleClickShowPassword}
                          onMouseDown={handleMouseDownPassword}
                          edge="end"
                        >
                          {values.showPassword ? (
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
      <Backdrop open={loadingOpen} style={{ zIndex: 100, color: "#fff" }}>
        <CircularProgress color="inherit" />
      </Backdrop>
    </Box>
  );
}
