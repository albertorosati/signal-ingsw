import React from "react";

import Paper from "@material-ui/core/Paper";
import InputBase from "@material-ui/core/InputBase";
import SearchIcon from "@material-ui/icons/Search";
import IconButton from "@material-ui/core/IconButton";

export default class Ricerca extends React.Component {
  render() {
    return (
      <Paper
        component="form"
        style={{
          backgroundColor: "white",
          padding: "3px",
          borderRadius: 5,
          boxShadow: "1px 3px 3px #9E9E9E",
        }}
        onSubmit={this.props.onSubmit}
      >
        <IconButton type="submit">
          <SearchIcon />
        </IconButton>
        <InputBase
          placeholder={this.props.placeholder}
          value={this.props.value}
          onChange={this.props.onChange}
        />
      </Paper>
    );
  }
}
