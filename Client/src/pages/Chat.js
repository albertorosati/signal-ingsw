import React from "react";
import Box from "@material-ui/core/Box";
import Card from "@material-ui/core/Card";
import Typography from "@material-ui/core/Typography";
import FormControl from "@material-ui/core/FormControl";
import SendIcon from "@material-ui/icons/Send";
import IconButton from "@material-ui/core/IconButton";
import InputLabel from "@material-ui/core/InputLabel";
import OutlinedInput from "@material-ui/core/OutlinedInput";
import CircularProgress from "@material-ui/core/CircularProgress";
import Moment from "react-moment";

export default class Chat extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      idSegnalazione: "",
      idChat: this.props.match.params.id,
      messaggio: "",
      messaggi: [
        {
          messaggio: "Ciao, sono Mario. Ho letto che ti server un aiuto per sistemare il rubinetto del lavandino. Quando posso venire a dare un'occhiata?",
          direction: "left",
          timestamp: new Date('June 18, 2021 10:27:00'),
        },
        {
          messaggio: "Io abito in zona Navile. Casa tua è molto lontana?",
          direction: "left",
          timestamp: new Date('June 18, 2021 10:29:00'),
        },
        {
          messaggio: "Piacere Mario, mi chiamo Nina. Grazie per aver preso in carico la mia segnalazione. ",
          direction: "right",
          timestamp: new Date('June 18, 2021 11:12:00'),
        },
        {
          messaggio: "Io abito in zona Corticella, non molto lontano da te. Saresti disponibile la settimana prossima?",
          direction: "right",
          timestamp: new Date('June 18, 2021 11:15:00'),
        },
        {
          messaggio: "Ottimo, siamo praticamente vicini :) Io ci sarei per martedì pomeriggio vero le 15.30. Per te va bene?",
          direction: "left",
          timestamp: new Date('June 18, 2021 13:46:00'),
        },
        {
          messaggio: "Va benissimo! Grazie Mario",
          direction: "right",
          timestamp: new Date('June 18, 2021 14:09:00'),
        },
      ],
    };

    //functions bind
    this.updateChat = this.updateChat.bind(this);
    this.sendMessage = this.sendMessage.bind(this);
  }

  appendMessage = (msg, dir) => {
    var newMessage = this.state.messaggi.concat({
      messaggio: msg,
      direction: dir,
      timestamp: new Date(),
    });
    this.setState({
      messaggi: newMessage,
      messaggio: "",
    });
  };

  updateChat() {
    const requestOptions = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        idSegnalazione: this.state.idSegnalazione,
        email: localStorage.getItem("email"),
      }),
    };
    fetch("/api/getChat", requestOptions)
      .then((res) => res.json())
      .then((data) => {
        if (data.state === "success") {
          this.state.idChat = data.idChat;
          //destinatario
          //numeri.forEach(valore => console.log(valore));
          data.messages.forEach((messaggio, direction) =>
            appendMessage(messaggio, direction)
          );
        }
      });
  }

  sendMessage() {
    const requestOptions = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        idChat: this.state.idChat,
        messaggio: this.state.messaggio,
      }),
    };

    fetch("/api/inviamessaggio", requestOptions)
      .then((res) => res.json())
      .then((data) => {
        if (data.state === "success") {
          //modify spunta
        }
      });
  }

  render() {
    return (
      <Box p={3} onLoad="setInterval('updateChat()',500)">
        {" "}
        {this.state.messaggi.map((item, index) => (
          <Card
            style={{
              padding: "10px",
              marginRight: item.direction == "left" ? "20%" : 0,
              marginLeft: item.direction == "right" ? "20%" : 0,
              marginBottom: "20px",
              opacity: item.sending ? 0.5 : 1,
            }}
          >
            {item.messaggio}
            <Typography
              style={{ marginTop: "5px" }}
              color="textSecondary"
              variant="caption"
              display="block"
            >
              {item.sending ? (
                <Box>
                  {" "}
                  <CircularProgress size={10} style={{ marginRight: 5 }} />
                  Invio in corso...
                </Box>
              ) : (
                <Box>
                  <Moment fromNow ago>
                    {item.timestamp}
                  </Moment>{" "}
                  fa
                </Box>
              )}
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
                  var joined_true = this.state.messaggi.concat({
                    messaggio: this.state.messaggio,
                    direction: "right",
                    sending: true,
                  });
                  var joined_false = this.state.messaggi.concat({
                    messaggio: this.state.messaggio,
                    direction: "right",
                    sending: false,
                  });
                  this.setState({
                    messaggi: joined_true,
                    messaggio: "",
                  });

                  setTimeout(
                    () => {
                      this.setState({messaggi: joined_false});
                    }, 
                    1000
                  );

                  //this.sendMessage();
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
