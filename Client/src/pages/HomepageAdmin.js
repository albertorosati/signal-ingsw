import React from "react";
import Box from "@material-ui/core/Box";
import Ricerca from "../components/Ricerca.js";
import Paper from "@material-ui/core/Paper";
import Typography from "@material-ui/core/Typography";
import Grid from "@material-ui/core/Grid";
import Avatar from "@material-ui/core/Avatar";

export default class Blank extends React.Component {
  render() {
    return (
      <Box p={2}>
        <Ricerca placeholder="Ricerca un utente..."></Ricerca>
        <Paper elevation={4} style={{ padding: 16, marginTop: 20 }}>
          <Grid
            container
            spacing={1}
            alignItems="center"
            justify="center"
            style={{ textAlign: "center" }}
          >
            <Grid item xs={12}>
              <center>
                <Avatar style={{ width: 100, height: 100 }} src="https://upload.wikimedia.org/wikipedia/commons/thumb/b/bc/Bologna-SanPetronioPiazzaMaggiore1.jpg/260px-Bologna-SanPetronioPiazzaMaggiore1.jpg"/>
              </center>
            </Grid>
            <Grid item xs={12}>
              <Typography variant="h5">
                Città Metropolitana di Bologna
              </Typography>
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
                  IN ATTESA DI APPROVAZIONE
                </Typography>
                <Typography variant="h5" gutterBottom>
                  6
                </Typography>
              </Paper>
            </Grid>
          </Grid>
        </Paper>
      </Box>
    );
  }
}
