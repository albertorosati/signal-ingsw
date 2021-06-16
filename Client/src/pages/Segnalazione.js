import React from "react";
import Box from "@material-ui/core/Box";
import Divider from "@material-ui/core/Divider";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import HeaderCardSegnalazione from "../components/HeaderCardSegnalazione.js";
import Mappa from "../components/Mappa.js";
import Image from "material-ui-image";
import SendIcon from "@material-ui/icons/Send";
import Skeleton from '@material-ui/lab/Skeleton';
import Paper from "@material-ui/core/Paper";

export default class Segnalazione extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      segnalazione: null,
    };
    this.getSegnalazione();
  }
  getSegnalazione() {
    const requestOptions = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ id: Number.parseInt(this.props.match.params.id) }),
    };
    fetch("/api/getSegnalazione", requestOptions)
      .then((res) => res.json())
      .then((data) => {
        console.log(data);
        this.setState({ segnalazione: data });
      })
      .catch((err) => console.log(err));
  }
  render() {
    const segnalazione = this.state.segnalazione;
    return (
      <Paper style={{ margin: 16, paddingBottom: 16 }}>
        <HeaderCardSegnalazione autore={(segnalazione) ? segnalazione.autore : ""} reputazione={(segnalazione) ? segnalazione.reputazione : ""}/>
        {segnalazione ? <Image src={segnalazione.imageSrc} aspectRatio={1.8} cover /> : <Skeleton variant="rect" height={400}/>}
        <Box py={3} px={3}>
          <Typography gutterBottom variant="h5">
            {segnalazione ? segnalazione.titolo : <Skeleton variant="text"/>}
          </Typography>
          <Typography variant="body2" style={{textAlign:'justify'}}>
            {segnalazione ? segnalazione.descrizione : <Skeleton variant="text"/>}
          </Typography>
        </Box>
        <Divider variant="middle" />
        <Box py={3} px={3}>
          <Typography gutterBottom variant="h5">
            Dove
          </Typography>
          {segnalazione ? (
            <Mappa zoom="100" lat={segnalazione.lat} lon={segnalazione.lon} 
            markers={[[segnalazione.lat, segnalazione.lon]]}/>
          ) : <Skeleton variant="rect" height={300}/>}
        </Box>
        <Divider variant="middle" />
        <Box my={3} px={3}>
          <Typography gutterBottom variant="h5">
            Intervieni
          </Typography>
          <Button
            variant="contained"
            color="primary"
            size="large"
            startIcon={<SendIcon />}
            fullWidth
          >
            Invia Richiesta
          </Button>
        </Box>
      </Paper>
    );
  }
}
