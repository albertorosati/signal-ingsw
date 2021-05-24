import React from "react";
import Box from "@material-ui/core/Box";
import TextField from "@material-ui/core/TextField";
import Autocomplete from "@material-ui/lab/Autocomplete";
import CircularProgress from "@material-ui/core/CircularProgress";
import InputAdornment from "@material-ui/core/InputAdornment";
import IconButton from "@material-ui/core/IconButton";
import SearchIcon from "@material-ui/icons/Search";

export default class Ricerca extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      open: false,
      results: [{ display_name: "ciao" }],
      loading: false,
      indirizzo: "",
    };
  }
  fetchIndirizzo() {
    this.setState({ loading: true });
    const requestOptions = {
      method: "GET",
      headers: { "Content-Type": "application/json" },
    };
    fetch(
      "https://nominatim.openstreetmap.org/search?q=" +
        this.state.indirizzo +
        "&format=json",
      requestOptions
    )
      .then((res) => res.json())
      .then((data) => {
        this.setState({
          results: data,
          loading: false,
        });
      })
      .catch((err) => console.log(err));
  }
  render() {
    return (
      <Box>
        <TextField
          id="standard-name"
          label="Ricerca Indirizzo"
          variant="outlined"
          fullWidth
          onChange={(e) => this.setState({ indirizzo: e.target.value })}
          InputProps={{
            endAdornment: (
              <IconButton
                onClick={(event) => {
                  this.fetchIndirizzo();
                }}
              >
                <SearchIcon />
              </IconButton>
            ),
          }}
        />
        <br />
        <br />

        <Autocomplete
          open={this.state.open}
          onOpen={() => {
            this.setState({ open: true });
          }}
          onClose={() => {
            this.setState({ open: false });
          }}
          getOptionSelected={(option, value) =>
            option.display_name === value.display_name
          }
          onInputChange={(event, newValue) => {
            this.setState({ indirizzo: newValue });
          }}
          noOptionsText="Nessun risultato, assicurati di aver effettuato la ricerca"
          getOptionLabel={(option) => option.display_name}
          options={this.state.results}
          loading={this.state.loading}
          onChange={this.props.onChange}
          renderInput={(params) => (
            <TextField
              {...params}
              label="Seleziona Indirizzo"
              variant="outlined"
              type="text"
              InputProps={{
                ...params.InputProps,
                endAdornment: (
                  <React.Fragment>
                    {this.state.loading ? (
                      <CircularProgress color="inherit" size={20} />
                    ) : null}
                    {params.InputProps.endAdornment}
                  </React.Fragment>
                ),
              }}
            />
          )}
        />
      </Box>
    );
  }
}
