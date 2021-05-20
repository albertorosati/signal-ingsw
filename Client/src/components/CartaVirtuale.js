import React from "react";

import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import Typography from "@material-ui/core/Typography";

export default class CardSegnalazione extends React.Component {
  render() {
    return (
      <Card style={{ marginTop: 16 }}>
        <CardContent>
          <center>
            <Typography variant="h6">{this.props.nome}</Typography>
            <img height="100" src={this.props.logo} style={{marginTop: 16, marginBottom: 16}} />
            <Typography variant="button" display="block" gutterBottom>
              PUNTI
            </Typography>
            <Typography variant="h4" gutterBottom>
              {this.props.punti}
            </Typography>
          </center>
        </CardContent>
      </Card>
    );
  }
}
