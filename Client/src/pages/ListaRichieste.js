import React from "react";
import Box from "@material-ui/core/Box";
import Paper from "@material-ui/core/Paper";
import Avatar from "@material-ui/core/Avatar";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import Divider from "@material-ui/core/Divider";
import IconButton from "@material-ui/core/IconButton";
import CheckIcon from "@material-ui/icons/CheckCircle";
import CancelIcon from "@material-ui/icons/Cancel";
import Rating from "@material-ui/lab/Rating";

const toInitials = (str) =>
  str
    .replace(/[^A-Z]/g, "")
    .concat(str.charAt(1).toUpperCase())
    .substring(0, 2);

export default class Blank extends React.Component {
  render() {
    return (
      <Box p={2}>
        <Paper style={{ padding: 16 }}>
          {[
            { name: "Maria Franchi", reputazione: 4.5 },
            { name: "Giovanni Rossi", reputazione: 4.0 },
            { name: "Giacomo Frusci", reputazione: 3.5 },
            { name: "Angela Mirelli", reputazione: 5.0 },
            { name: "Ettore Casanuova", reputazione: 2.0 }
          ].map((item, index) => (
            <Box mb={2}>
              <Grid container wrap="nowrap" spacing={2}>
                <Grid item>
                  <Avatar>{toInitials(item.name)}</Avatar>
                </Grid>
                <Grid item xs>
                  <Typography>{item.name}</Typography>
                  <Rating
                    name="half-rating-read"
                    defaultValue={item.reputazione}
                    precision={0.5}
                    readOnly
                  />
                </Grid>
                <Grid item xl>
                  <IconButton style={{ color: "green" }}>
                    <CheckIcon />
                  </IconButton>
                  <IconButton style={{ color: "red" }}>
                    <CancelIcon />
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
