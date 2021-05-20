import React from "react";
import Typography from "@material-ui/core/Typography";
import Box from "@material-ui/core/Box";
import Grid from "@material-ui/core/Grid";

import Icon from "@material-ui/icons/RadioButtonCheckedRounded";

export default class Ricerca extends React.Component {
  renderSwitch(n) {
    switch (n) {
      case 1:
        return {
          color: "#FFCC18",
          text: "In attesa di approvazione",
        };
      case 2:
        return {
          color: "#F1FF2D",
          text: "Approvata",
        };
      case 3:
        return {
          color: "#6DE958",
          text: "Disponibile",
        };
      case 4:
        return {
          color: "#BBD2EE",
          text: "Richiesta di presa in carico",
        };
      case 5:
        return {
          color: "#1A54BF",
          text: "Lavori in corso",
        };
      case 6:
        return {
          color: "#00C956",
          text: "Richiesta di terminazione",
        };
      case 7:
        return {
          color: "#FFFFFF",
          text: "Conclusa",
        };
      case 8:
        return {
          color: "#FF0000",
          text: "Rifiutata",
        };
      case 9:
        return {
          color: "#777071",
          text: "Sospesa",
        };
      default:
        return {
          color: "black",
          text: "INVALID",
        };
    }
  }
  render() {
    const status = this.renderSwitch(this.props.stato)
    return (
      <Grid container alignItems="center" spacing={1}>
        <Grid item>
          <Icon style={{color: status.color }}/>
        </Grid>
        <Grid item>
          <Typography variant="overline">{status.text}</Typography>
        </Grid>
      </Grid>
    );
  }
}
