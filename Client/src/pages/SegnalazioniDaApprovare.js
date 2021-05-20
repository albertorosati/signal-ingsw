import React from "react";
import Box from "@material-ui/core/Box";
import Divider from "@material-ui/core/Divider";
import Typography from "@material-ui/core/Typography";
import Card from "@material-ui/core/Card";
import HeaderCardSegnalazione from "../components/HeaderCardSegnalazione.js";
import Grid from "@material-ui/core/Grid";
import Button from "@material-ui/core/Button";
import Mappa from "../components/Mappa.js";
import Image from "material-ui-image";
import Chip from '@material-ui/core/Chip';


import CheckIcon from "@material-ui/icons/Check";
import ClearIcon from "@material-ui/icons/Clear";

export default class Blank extends React.Component {
  render() {
    return (
      <Box p={2}>
        <Card>
          <HeaderCardSegnalazione autore="Mario Rossi" reputazione="100" />

          <Divider variant="middle" />
          <Box my={3} px={3}>
            <Typography gutterBottom variant="h5">
              Dettagli della segnalazione
            </Typography>
            <Typography
              gutterBottom
              variant="overline"
              style={{ textDecorationLine: "underline" }}
            >
              Titolo
            </Typography>
            <Typography gutterBottom variant="body1">
              Lorem Ipsum
            </Typography>
            <Typography
              gutterBottom
              variant="overline"
              style={{ textDecorationLine: "underline" }}
            >
              Descrizione
            </Typography>
            <Typography
              gutterBottom
              variant="body1"
              style={{ textAlign: "justify" }}
            >
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam et
              orci nec ligula mattis laoreet. Aenean faucibus suscipit ex, in
              elementum nibh consequat at. Pellentesque habitant morbi tristique
              senectus et netus et malesuada fames ac turpis egestas.
            </Typography>
            <Typography
              gutterBottom
              variant="overline"
              style={{ textDecorationLine: "underline" }}
            >
              Lista Tag
            </Typography>
            <Box>

            {[
                { text: "Lorem" },
                { text: "Ipsum" },
                { text: "Dolor" }
              ].map((item, index) =>
              <Chip label={item.text} color="primary" style={{marginRight: "5px"}}/>
            )
            }

            </Box>
          </Box>
          <Divider variant="middle" />
          <Box my={3} px={3}>
            <Typography gutterBottom variant="h5">
              Contenuti Multimediali
            </Typography>
            <Image src="https://www.laleggepertutti.it/wp-content/uploads/2017/11/buca-strada.jpg" aspectRatio={1.8} cover />
          </Box>

          <Divider variant="middle" />
          <Box my={3} px={3}>
            <Typography gutterBottom variant="h5">
              Indirizzo
            </Typography>

            <Mappa />
          </Box>

          <Grid container spacing={3} style={{ padding: "20px" }}>
            <Grid item xs={6}>
              <Button
                variant="contained"
                style={{ backgroundColor: "green", color: "white" }}
                size="large"
                startIcon={<CheckIcon />}
                fullWidth
              >
                Approva
              </Button>
            </Grid>
            <Grid item xs={6}>
              <Button
                variant="contained"
                style={{ backgroundColor: "red", color: "white" }}
                size="large"
                startIcon={<ClearIcon />}
                fullWidth
              >
                Scarta
              </Button>
            </Grid>
          </Grid>
        </Card>
      </Box>
    );
  }
}
