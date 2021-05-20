import React from "react";
import Radio from "@material-ui/core/Radio";
import RadioGroup from "@material-ui/core/RadioGroup";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Box from "@material-ui/core/Box";
import Card from "@material-ui/core/Card";
import FormLabel from "@material-ui/core/FormLabel";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";

export default class MetodoPagamento extends React.Component {
  render() {
    return (
      <Box p={3}>
        <Card style={{ padding: "20px" }}>
          <FormLabel component="legend">Metodo di Pagamento</FormLabel>
          <RadioGroup aria-label="gender" name="gender1">
            <FormControlLabel value="Punti" control={<Radio />} label="Punti" />
            <FormControlLabel
              value="denaro"
              control={<Radio />}
              label="Denaro"
            />
          </RadioGroup>
          <br />
          <TextField
            label="Quantità"
            type="number"
            InputLabelProps={{
              shrink: true,
            }}
            fullWidth
          />
          <br />
          <br />
          <Button variant="contained" color="primary" fullWidth>
            Invia
          </Button>
        </Card>
      </Box>
    );
  }
}
