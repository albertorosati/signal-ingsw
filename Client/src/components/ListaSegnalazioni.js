import React from "react";

import CardSegnalazione from "./CardSegnalazione.js";
import SegnalazioneNonTrovata from "./SegnalazioneNonTrovata";
import Grid from "@material-ui/core/Grid";

export default class ListaSegnalazioni extends React.Component {
  render() {
    return (
      <Grid container spacing={3}>
        {this.props.segnalazioni ? (
          !(this.props.segnalazioni instanceof Array) || this.props.segnalazioni.length == 0 ? (
            <SegnalazioneNonTrovata />
          ) : (
            this.props.segnalazioni.map((item, index) => (
              <Grid item lg={4} md={6} xs={12}>
                <CardSegnalazione
                  autore={item.autore}
                  reputazione={item.reputazione}
                  titolo={item.titolo}
                  foto={item.imageSrc}
                  id={item.id}
                  pub_time={item.timestamp}
                  stato={item.stato}
                  userType="pro"
                />
              </Grid>
            ))
          )
        ) : (
          [...Array(3)].map((x, i) => (
            <Grid item lg={4} md={6} xs={12}>
              <CardSegnalazione />
            </Grid>
          ))
        )}
      </Grid>
    );
  }
}
