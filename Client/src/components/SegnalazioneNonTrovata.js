import React from "react";
import Grid from "@material-ui/core/Grid";
import PuzzleImage from "../res/Puzzle.svg";

export default class SegnalazioneNonTrovata extends React.Component {
  render() {
    return (
      <Grid
        container
        alignItems="center"
        justify="center"
        direction="column"
        style={{ minHeight: "85vh", overflow: "hidden" }}
      >
        <Grid item>
          <img src={PuzzleImage} width={300} />
        </Grid>
        <Grid item>
          <h1>Nessuna segnalazione trovata</h1>
        </Grid>
      </Grid>
    );
  }
}
