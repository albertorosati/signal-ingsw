import React from "react";
import Box from "@material-ui/core/Box";

import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import Grid from "@material-ui/core/Grid";

import Paper from "@material-ui/core/Paper";

import TextField from "@material-ui/core/TextField";

import FormControl from "@material-ui/core/FormControl";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import FormLabel from "@material-ui/core/FormLabel";

import Radio from "@material-ui/core/Radio";
import RadioGroup from "@material-ui/core/RadioGroup";

import { Link, useHistory } from "react-router-dom";

import Backdrop from "@material-ui/core/Backdrop";
import CircularProgress from "@material-ui/core/CircularProgress";

import Dialog from "@material-ui/core/Dialog";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogActions from '@material-ui/core/DialogActions';
import DialogTitle from "@material-ui/core/DialogTitle";

import MuiAlert from "@material-ui/lab/Alert";

import CommunityImage from "../res/community.png";

export default function Registrazione() {
  const [values, setValues] = React.useState({
    tipo: "base",
    nome: "",
    cognome: "",
    email: "",
    password: "",
    confermaPassword: "",
    identificatore: "",
    comune: "",
    registrationError: false,
    codiceFiscaleError: false,
  });

  const [dialogOpen, setOpenDialog] = React.useState(false);
  const [loadingOpen, setOpenLoading] = React.useState(false);

  const history = useHistory();

  const handleChange = (prop) => (event) => {
    setValues({ ...values, [prop]: event.target.value });
  };

  function validaCodiceFiscale(cf) {
    var validi, i, s, set1, set2, setpari, setdisp;
    if (cf == "") return "";
    cf = cf.toUpperCase();
    if (cf.length != 16) return false;
    validi = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    for (i = 0; i < 16; i++) {
      if (validi.indexOf(cf.charAt(i)) == -1) return false;
    }
    set1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    set2 = "ABCDEFGHIJABCDEFGHIJKLMNOPQRSTUVWXYZ";
    setpari = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    setdisp = "BAKPLCQDREVOSFTGUHMINJWZYX";
    s = 0;
    for (i = 1; i <= 13; i += 2)
      s += setpari.indexOf(set2.charAt(set1.indexOf(cf.charAt(i))));
    for (i = 0; i <= 14; i += 2)
      s += setdisp.indexOf(set2.charAt(set1.indexOf(cf.charAt(i))));
    if (s % 26 != cf.charCodeAt(15) - "A".charCodeAt(0)) return false;
    return true;
  }

  const callAPI = (e) => {
    e.preventDefault();
    if (!validaCodiceFiscale(values.identificatore)) {
      setValues({ ...values, codiceFiscaleError: true });
      return;
    }
    setOpenLoading(true);
    const requestOptions = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        email: values.email,
        password: values.password,
        nome: values.nome,
        cognome: values.cognome,
        identificatore: values.identificatore,
        comune: values.comune,
        tipoUtente: values.tipo == "base" ? 0 : 1
      }),
    };
    fetch("/api/registrazione", requestOptions)
      .then((res) => res.json())
      .then((data) => {
        setOpenLoading(false);
        if (data.state === "success") {
          setOpenDialog(true);
        } else {
          handleChange({ registrationError: true, email: "", password: "" });
        }
      })
      .catch((err) => console.log(err));
  };

  return (
    <Box style={{ background: "linear-gradient(#7274ed, #c76d5a)" }}>
      <Dialog
        open={dialogOpen}
      >
        <DialogTitle id="alert-dialog-title">{"Ottimo lavoro!"}</DialogTitle>
        <DialogContent>
          <DialogContentText>
            La registrazione è andata a buon fine. Il prossimo passo è quello di confermare la mail.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={e=>history.push("/login")} color="primary">
            Torna al Login
          </Button>
        </DialogActions>
      </Dialog>

      <Grid
        container
        spacing={0}
        direction="column"
        alignItems="center"
        justify="center"
        style={{ minHeight: "100vh", overflow: "hidden" }}
      >
        <Grid item>
          <img src={CommunityImage} width="150" />
          <br />
          <br />
        </Grid>
        <Grid item>
          <Typography variant="h6" style={{ color: "white" }}>
            Entra a far parte della nostra comunità!
          </Typography>
          <br />
        </Grid>

        {values.registrationError ? (
          <Grid item>
            <MuiAlert elevation={6} variant="filled" severity="error" fullWidth>
              La registrazione non è andata a buon
              <br /> fine per un errore interno. Riprova più tardi!
            </MuiAlert>
            <br />
          </Grid>
        ) : null}

        <Grid item>
          <Paper style={{ padding: 16 }}>
            <form onSubmit={callAPI}>
              <FormControl component="fieldset">
                <FormLabel component="legend">Tipologia di Utente</FormLabel>
                <RadioGroup
                  aria-label="tipo"
                  name="tipo"
                  value={values.tipo}
                  onChange={handleChange("tipo")}
                >
                  <FormControlLabel
                    value="base"
                    control={<Radio />}
                    label="Utente Base"
                  />
                  <FormControlLabel
                    value="pro"
                    control={<Radio />}
                    label="Utente Pro"
                  />
                </RadioGroup>
              </FormControl>

              <br />

              <TextField
                id="email"
                type="email"
                value={values.email}
                onChange={handleChange("email")}
                label="E-Mail"
                fullWidth
                required
              />

              <br />

              <TextField
                id="password"
                value={values.password}
                onChange={handleChange("password")}
                type={values.showPassword ? "text" : "password"}
                label="Password"
                fullWidth
                required
              />

              <br />

              <TextField
                id="nome"
                value={values.nome}
                onChange={handleChange("nome")}
                label="Nome"
                fullWidth
                required
              />

              <br />

              <TextField
                id="cognome"
                value={values.cognome}
                onChange={handleChange("cognome")}
                label="Cognome"
                fullWidth
                required
              />

              <br />

              <TextField
                id="comune"
                value={values.comune}
                onChange={handleChange("comune")}
                label="Comune"
                fullWidth
                required
              />

              <br />

              <TextField
                fullWidth
                label={values.tipo == "base" ? "Codice Fiscale" : "Partita IVA"}
                value={values.identificatore}
                onChange={handleChange("identificatore")}
                required
                error={values.codiceFiscaleError}
                helperText={
                  values.codiceFiscaleError ? "Codice fiscale non valido" : ""
                }
              />

              <br />

              <Box mt={2}>
                <Button
                  variant="contained"
                  size="large"
                  color="default"
                  style={{ marginRight: "10px" }}
                  component={Link}
                  to="/login"
                >
                  Annulla
                </Button>

                <Button
                  type="submit"
                  variant="contained"
                  size="large"
                  color="primary"
                >
                  Registrati
                </Button>
              </Box>
            </form>
          </Paper>
        </Grid>
      </Grid>
      <Backdrop open={loadingOpen} style={{ zIndex: 100, color: "#fff" }}>
        <CircularProgress color="inherit" />
      </Backdrop>
    </Box>
  );
}
