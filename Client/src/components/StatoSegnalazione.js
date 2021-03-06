import React from "react";
import Typography from "@material-ui/core/Typography";
import Box from "@material-ui/core/Box";
import Grid from "@material-ui/core/Grid";
import Skeleton from "@material-ui/lab/Skeleton";

import Icon from "@material-ui/icons/RadioButtonCheckedRounded";

export default class Ricerca extends React.Component {
  renderSwitch(n) {
    switch (n) {
      case 0:
        return {
          color: "#FFCC18",
          text: "In attesa di approvazione",
        };
      case 1:
        return {
          color: "#F1FF2D",
          text: "Approvata",
        };
      case 2:
        return {
          color: "#6DE958",
          text: "Disponibile",
        };
      case 3:
        return {
          color: "#BBD2EE",
          text: "Richiesta di presa in carico",
        };
      case 4:
        return {
          color: "#1A54BF",
          text: "Lavori in corso",
        };
      case 5:
        return {
          color: "#00C956",
          text: "Richiesta di terminazione",
        };
      case 6:
        return {
          color: "#FFFFFF",
          text: "Conclusa",
        };
      case 7:
        return {
          color: "#FF0000",
          text: "Rifiutata",
        };
      case 8:
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
    const status = this.renderSwitch(this.props.stato);
    return status.text === "INVALID" ? (
      <Skeleton width={80} />
    ) : (
      <Grid container alignItems="center" spacing={1}>
        <Grid item>
          <Icon style={{ color: status.color }} />
        </Grid>
        <Grid item>
          <Typography variant="overline">{status.text}</Typography>
        </Grid>{" "}
      </Grid>
    );
  }
}
