import React from "react";
import Paper from "@material-ui/core/Paper";
import Avatar from "@material-ui/core/Avatar";
import Typography from "@material-ui/core/Typography";
import Rating from "@material-ui/lab/Rating";
import Box from "@material-ui/core/Box";
import Grid from "@material-ui/core/Grid";
import CartaVirtuale from "../components/CartaVirtuale.js";

const toInitials = (str) =>
  str
    .replace(/[^A-Z]/g, "")
    .concat(str.charAt(1).toUpperCase())
    .substring(0, 2);

export default class Profilo extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      reputazione: "",
      totSegnalazioniEffettuate: "...",
      totSegnalazioniRisolte: "...",
      carte: [
        {
          titolo: "Città Metropolitana di Bologna",
          image: "advewv516vw165165==",
          points: "",
        },
      ],
    };

    this.getInfo();
  }

  appendCard(titolo, img, punti) {
    var card = this.state.carte.concat({
      titolo: titolo,
      image: img,
      points: punti,
    });
    this.setState({
      carte: card,
    });
  }

  getInfo() {
    const requestOptions = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        email: localStorage.getItem("email"),
      }),
    };

    fetch("/api/getProfiloPersonale", requestOptions)
      .then((res) => res.json())
      .then((data) => {
        if (data.state === "success") {
          this.state.idChat = data.idChat;
          this.setState({reputazione: data.reputazione});
          this.setState({totSegnalazioniEffettuate: data.segnalazioniTotali});
          this.setState({totSegnalazioniRisolte: data.segnalazioniRisolte});
          data.carte.forEach((titolo, img, punti) =>
            this.appendCard(titolo, img, punti)
          );
        }
      });
  }

  render() {
    return (
      <Box p={3}>
        <Paper style={{ padding: 16 }}>
          <Grid
            container
            spacing={1}
            alignItems="center"
            justify="center"
            style={{ textAlign: "center" }}
          >
            <Grid item xs={12}>
              <center>
                <Avatar style={{ width: 100, height: 100, fontSize: 50 }}>
                  {toInitials(
                    localStorage.getItem("nome") +
                      " " +
                      localStorage.getItem("cognome")
                  )}
                </Avatar>
              </center>
            </Grid>
            <Grid item xs={12}>
              <Typography variant="h4">
                {localStorage.getItem("nome") +
                  " " +
                  localStorage.getItem("cognome")}
              </Typography>
            </Grid>
            <Grid item xs={12}>
              <Typography component="legend">Reputazione</Typography>
              <Rating
                name="half-rating-read"
                defaultValue={4.5}
                precision={0.5}
                readOnly
              />
            </Grid>
            <Grid item xs={6}>
              <Paper
                style={{
                  padding: 8,
                  backgroundColor: "#f5f5f5",
                }}
                elevation={0}
              >
                <Typography variant="button" display="block" gutterBottom>
                  SEGNALAZIONI TOTALI
                </Typography>
                <Typography variant="h5" gutterBottom>
                  {this.state.totSegnalazioniEffettuate}
                </Typography>
              </Paper>
            </Grid>
            <Grid item xs={6}>
              <Paper
                style={{
                  padding: 8,
                  backgroundColor: "#f5f5f5",
                }}
                elevation={0}
              >
                <Typography variant="button" display="block" gutterBottom>
                  SEGNALAZIONI RISOLTE
                </Typography>
                <Typography variant="h5" gutterBottom>
                  {this.state.totSegnalazioniRisolte}
                </Typography>
              </Paper>
            </Grid>
          </Grid>
        </Paper>
        <Typography style={{ padding: 16, marginTop: 16 }} variant="h5">
          Carte Virtuali
        </Typography>
        {this.state.carte.map((item, index) => (
          <CartaVirtuale
            nome={item.titolo}
            punti={item.points}
            logo={item.image}
          />
        ))}
      </Box>
    );
  }
}
