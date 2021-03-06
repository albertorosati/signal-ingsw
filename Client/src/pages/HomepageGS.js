import React from "react";
import Box from "@material-ui/core/Box";
import Paper from "@material-ui/core/Paper";
import TextField from '@material-ui/core/TextField';

export default class Blank extends React.Component {
  render() {
    return (
      <Box p={2}>
        <Paper style={{ padding: 16, marginTop: 20 }}>
        <TextField
          multiline
          defaultValue={
            "[10/05/2021 19:10:21] 71.144.36.202\t- '3b1d8994' ha effettuato il login\n" +
            "[10/05/2021 19:10:40] 158.138.246.49\t- '06e4975d' ha effettuato il login\n" +
            "[10/05/2021 19:11:14] 38.243.250.243\t- 'a584509f' ha effettuato il login\n" +
            "[10/05/2021 19:11:18] 44.19.221.35\t- '15c756a3' ha effettuato una segnalazione\n" +
            "[10/05/2021 19:11:30] 236.3.159.156\t- '463d5c4b' ha effettuato il login\n" +
            "[10/05/2021 19:11:45] 154.162.16.39\t- '306cefdb' ha effettuato il login\n" +
            "[10/05/2021 19:12:04] 71.144.36.202\t- '3b1d8994' ha effettuato una segnalazione\n" +
            "[10/05/2021 19:12:12] 63.31.32.213\t- '691dac98' ha effettuato il login\n" +
            "[10/05/2021 19:12:43] 63.31.32.213\t- '691dac98' ha preso in carico una segnalazione"
          }
          style={{"font-family": "monospace"}}
          variant="outlined"
          InputProps={{
            readOnly: true,
            style: { fontFamily: "monospace" }
          }}
          fullWidth
        />
        </Paper>
      </Box>
    );
  }
}
