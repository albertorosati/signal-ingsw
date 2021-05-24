import React from "react";
import Box from "@material-ui/core/Box";
import Paper from "@material-ui/core/Paper";
import Avatar from "@material-ui/core/Avatar";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import Divider from "@material-ui/core/Divider";
import IconButton from "@material-ui/core/IconButton";
import CheckIcon from "@material-ui/icons/CheckCircle";
import ArrowForwardIcon from '@material-ui/icons/ArrowForward';
import Rating from "@material-ui/lab/Rating";


export default class Blank extends React.Component {
  render() {
    return (
      <Box p={2}>
        <Paper style={{ padding: 16 }}>
          {[
            { name: "Maria Franchi", img: "https://bit.ly/3wg62js", titolo: "Buca in Via Mazzini" },
            { name: "Giovanni Rossi", img: "https://bit.ly/3ot3BY5", titolo: "Tubatura rotta, allagamento" },
            { name: "Giacomo Frusci", img: "http://www.corrieredelconero.it/wp-content/uploads/2018/05/cimitero-jesi-24-mag-730x548.jpg", titolo: "Rami in strada" },
          ].map((item, index) => (
            <Box mb={2}>
              <Grid container wrap="nowrap" spacing={2}>
                <Grid item>
                  <Avatar src={item.img}>{item.name}</Avatar>
                </Grid>
                <Grid item xs>
                  <Typography><b>{item.name}</b></Typography>
                  <Typography variant="description">{item.titolo}</Typography>
                </Grid>
                <Grid item xl>
                  <IconButton>
                    <ArrowForwardIcon />
                  </IconButton>
                </Grid>
              </Grid>
              <Divider style={{ marginTop: "10px" }} />
            </Box>
          ))}
        </Paper>
      </Box>
    );
  }
}
