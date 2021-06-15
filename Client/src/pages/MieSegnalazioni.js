import React from "react";

import CardSegnalazione from "../components/CardSegnalazione.js";

import Box from "@material-ui/core/Box";
import Grid from "@material-ui/core/Grid";

import PuzzleImage from "../res/Puzzle.svg";
import SegnalazioneNonTrovata from "../components/SegnalazioneNonTrovata.js";
import ListaSegnalazioni from "../components/ListaSegnalazioni.js";

export default class MieSegnalazioni extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      segnalazioni: null,
    };
    this.getSegnalazioni();
  }
  getSegnalazioni() {
    const requestOptions = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ id: localStorage.getItem("id") }),
    };
    fetch("/api/getListaSegnalazioni", requestOptions)
      .then((res) => res.json())
      .then((data) => {
        this.setState({ segnalazioni: data });
      })
      .catch((err) => console.log(err));
  }
  render() {
    return (
      <Box p={3}>
        <ListaSegnalazioni segnalazioni={this.state.segnalazioni}/>
      </Box>
    );
  }
}
