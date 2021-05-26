import React from "react";
import Box from "@material-ui/core/Box";
import Card from "@material-ui/core/Card";
import Typography from "@material-ui/core/Typography";
import FormControl from "@material-ui/core/FormControl";
import SendIcon from "@material-ui/icons/Send";
import IconButton from "@material-ui/core/IconButton";
import InputLabel from "@material-ui/core/InputLabel";
import OutlinedInput from "@material-ui/core/OutlinedInput";
import Moment from "react-moment";

export default class Chat extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      messaggio: "",
      messaggi: [
        {
          messaggio: "Ciao a tutti, questo è un messaggio di prova",
          direction: "left",
          timestamp: new Date(),
        },
        {
          messaggio: "Questo è un altro messaggio di prova",
          direction: "left",
          timestamp: new Date(),
        },
        {
          messaggio: "Questo messaggio invece è sulla destra",
          direction: "right",
          timestamp: new Date(),
        },
      ],
    };
  }
  render() {
    return (
      <Box p={3}>
        {this.state.messaggi.map((item, index) => (
          <Card
            style={{
              padding: "10px",
              marginRight: item.direction == "left" ? "20%" : 0,
              marginLeft: item.direction == "right" ? "20%" : 0,
              marginBottom: "20px",
            }}
          >
            {item.messaggio}
            <Typography
              style={{ marginTop: "5px" }}
              color="textSecondary"
              variant="caption"
              display="block"
            >
              <Moment fromNow ago>
                {item.timestamp}
              </Moment>{" "}
              fa
            </Typography>
          </Card>
        ))}

        <FormControl fullWidth variant="outlined">
          <InputLabel>Messaggio</InputLabel>
          <OutlinedInput
            endAdornment={
              <IconButton
                color="primary"
                onClick={(e) => {
                  var joined = this.state.messaggi.concat({
                    messaggio: this.state.messaggio,
                    direction: "right",
                  });
                  this.setState({
                    messaggi: joined,
                    messaggio: "",
                  });
                }}
              >
                <SendIcon />
              </IconButton>
            }
            value={this.state.messaggio}
            onChange={(e) => this.setState({ messaggio: e.target.value })}
            labelWidth={77}
          />
        </FormControl>
      </Box>
    );
  }
}
