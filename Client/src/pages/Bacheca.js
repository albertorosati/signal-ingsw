import React from "react";

import Fab from "@material-ui/core/Fab";
import AddIcon from "@material-ui/icons/Add";
import Box from "@material-ui/core/Box";
import Mappa from "../components/Mappa.js";
import ListaSegnalazioni from "../components/ListaSegnalazioni.js";
import Typography from "@material-ui/core/Typography";

import { Link } from "react-router-dom";

import Ricerca from "../components/Ricerca.js";

export default class Bacheca extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      segnalazioni: null,
      mapMarkers: [],
      ricerca: "",
      showSearch: false
    };
    this.getSegnalazioni({});
  }
  getSegnalazioni(options) {
    const requestOptions = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        email: localStorage.getItem("email"),
        homePage: true,
      }),
    };
    fetch("/api/getListaSegnalazioni", requestOptions)
      .then((res) => res.json())
      .then((data) => {
        this.setState({ segnalazioni: data.risultatiRicerca });
        var mapMarkerArray = [];
        this.state.segnalazioni.map((item, index) => {
          mapMarkerArray.push([item.lat, item.lon]);
        });
        this.setState({ mapMarkers: mapMarkerArray });
      })
      .catch((err) => console.log(err));
  }
  render() {
    return (
      <Box>
        <Fab
          color="primary"
          component={Link}
          to="/nuovaSegnalazione"
          variant="extended"
          aria-label="add"
          style={{
            position: "absolute",
            zIndex: 2,
            top: "68vh",
            right: 0,
            marginRight: "20px",
            textDecoration: "none",
          }}
        >
          <AddIcon />
          Aggiungi
        </Fab>

        <Box
          style={{
            position: "absolute",
            zIndex: 2,
            top: "70px",
            width: "100%",
            paddingLeft: 10,
            paddingRight: 10,
          }}
        >
          <Ricerca
            placeholder="Ricerca una segnalazione..."
            value={this.state.ricerca}
            onChange={(e) => this.setState({ ricerca: e.target.value })}
            onSubmit={(e) => {
              e.preventDefault();
              this.setState({segnalazioni: null, showSearch: true});
              this.getSegnalazioni({ search: this.state.ricerca });
            }}
          />
        </Box>

        <Mappa markers={this.state.mapMarkers} />

        <Box p={2}>
          {this.state.showSearch ? (
            <Typography variant="h5" gutterBottom>
              Risultati Ricerca "{this.state.ricerca}"
            </Typography>
          ) : null}
          <ListaSegnalazioni segnalazioni={this.state.segnalazioni} />
        </Box>
      </Box>
    );
  }
}
