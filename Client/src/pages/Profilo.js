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
                  10
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
                  6
                </Typography>
              </Paper>
            </Grid>
          </Grid>
        </Paper>
        <Typography style={{ padding: 16, marginTop: 16 }} variant="h5">
          Carte Virtuali
        </Typography>
        {[
          {
            nome: "Città Metropolitana di Bologna",
            punti: "1700",
            logo:
              "https://upload.wikimedia.org/wikipedia/it/6/6e/Bologna-Stemma.png",
          },
          {
            nome: "Comune di Castel Maggiore",
            punti: "500",
            logo:
              "https://upload.wikimedia.org/wikipedia/it/archive/7/73/20150704124804%21Castel_Maggiore-Stemma.png",
          },
        ].map((item, index) => (
          <CartaVirtuale nome={item.nome} punti={item.punti} logo={item.logo} />
        ))}
      </Box>
    );
  }
}
