import React from "react";
import TextField from "@material-ui/core/TextField";
import Divider from "@material-ui/core/Divider";
import Button from "@material-ui/core/Button";
import Box from "@material-ui/core/Box";
import Card from "@material-ui/core/Card";
import CloudUploadIcon from "@material-ui/icons/CloudUpload";
import Typography from "@material-ui/core/Typography";
import ChipInput from "material-ui-chip-input";
import HelpIcon from "@material-ui/icons/Help";

import CasellaIndirizzo from "../components/CasellaIndirizzo";

import { withStyles } from "@material-ui/core/styles";

const useStyles = (theme) => ({
  caption: {
    display: "flex",
    alignItems: "center",
    marginBottom: 10,
  },
  icon: {
    fontSize: 25,
    marginRight: 5,
    color: "grey",
  },
});

class NuovaSegnalazione extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      titolo: "",
      descrizione: "",
      tags: [],
      indirizzo: [],
    };
  }

  sendSeg = (e) => {
    e.preventDefault();
    console.log(this.state);
    const requestOptions = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        email: localStorage.getItem("email"),
        titolo: this.state.titolo,
        descrizione: this.state.descrizione,
        tags: this.state.tags,
        lat: this.indirizzo.lat,
        lon: this.indirizzo.lon,
        comune: "test",
      }),
    };
    
    console.log(requestOptions)

    fetch("/api/nuovaSegnalazione", requestOptions)
      .then((res) => res.json())
      .then((data) => {
        if (data.state === "success") {
          //show success
          console.log("Success")
          console.log(data)
          this.props.history.push("/bacheca");          
        } else {
          //show error message
          console.log("error")
          console.log(data)
        }
        
      })
      .catch((err) => console.log(err));
  };

  render() {
    const { classes } = this.props;
    return (
      <Box p={3}>
        <Card style={{ padding: "20px" }}>
          <form noValidate autoComplete="off" onSubmit={this.sendSeg}>
            <Typography variant="caption" className={classes.caption}>
              <HelpIcon className={classes.icon} />
              Inserisci un titolo sintetico ed efficace, in modo che la nostra
              comunità possa intervenire al più presto (Max. 50 caratteri)
            </Typography>
            <TextField
              label="Titolo"
              variant="outlined"
              fullWidth
              onChange={(e) => this.setState({ titolo: e.target.value })}
            />
            <br></br>
            <br></br>
            <Typography variant="caption" className={classes.caption}>
              <HelpIcon className={classes.icon} />
              Inserisci una descrizione dettagliata del problema, ma stai
              attento a non rivelare troppe informazioni sensibili. (Max. 1000
              caratteri)
            </Typography>
            <TextField
              label="Descrizione"
              variant="outlined"
              multiline
              rows={2}
              rowsMax={Infinity}
              fullWidth
              onChange={(e) => this.setState({ descrizione: e.target.value })}
            />
            <br></br>
            <br></br>
            <Typography variant="caption" className={classes.caption}>
              <HelpIcon className={classes.icon} />
              Inserisci delle parole chiave che possano racchiudere in un
              singolo termine il problema. (Max. 5)
            </Typography>
            <ChipInput
              variant="outlined"
              defaultValue={this.state.tags}
              onChange={(chips) => this.setState({ tags: chips })}
              label="Tags"
              fullWidth
            />
            <br></br>
            <br></br>
            <Typography variant="caption" className={classes.caption}>
              <HelpIcon className={classes.icon} />
              Inserisci l'indirizzo dove si è verificato il problema.
              Attenzione! Non inserire informazioni sensibili
            </Typography>
            <CasellaIndirizzo
              onChange={(e, value) => this.setState({ indirizzo: value })}
            />
            <br></br>
            <br></br>
            <Divider />
            <br></br>
            <Button
              variant="contained"
              color="secondary"
              fullWidth
              size="large"
              startIcon={<CloudUploadIcon />}
            >
              Carica un'immagine
            </Button>
            <br></br>
            <br></br>
            <Button
              type="submit"
              variant="contained"
              color="primary"
              fullWidth
              size="large"
              //onClick={(e) => console.log(this.state)}
            >
              INVIA
            </Button>
          </form>
        </Card>
      </Box>
    );
  }
}
export default withStyles(useStyles)(NuovaSegnalazione);
