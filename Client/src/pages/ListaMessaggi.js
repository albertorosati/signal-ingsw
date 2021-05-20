import React from "react";
import Box from "@material-ui/core/Box";
import { makeStyles } from "@material-ui/core/styles";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import Divider from "@material-ui/core/Divider";
import ListItemText from "@material-ui/core/ListItemText";
import ListItemAvatar from "@material-ui/core/ListItemAvatar";
import Avatar from "@material-ui/core/Avatar";
import Typography from "@material-ui/core/Typography";
import { Link } from "react-router-dom";

export default class ListaMessaggi extends React.Component {
  render() {
    return (
      <Box>
        <List>
          {[
            {
              name: "Anna Ciampolini",
              title: "La strada è molto sporca",
              message: "Buongiorno Cristina",
            },
            {
              name: "Enzo Miccio",
              title: "Ma che orrore!",
              message: "Ma come perdindirindina ti vesti?",
            },
          ].map((item, index) => (
            <div>
              <ListItem
                alignItems="flex-start"
                button
                component={Link}
                to="/chat"
              >
                <ListItemAvatar>
                  <Avatar alt={item.name} src="/static/images/avatar/1.jpg" />
                </ListItemAvatar>
                <ListItemText
                  primary={item.title}
                  secondary={
                    <React.Fragment>
                      <Typography
                        component="span"
                        variant="body2"
                        color="textPrimary"
                      >
                        {item.name}
                      </Typography>
                      <span> - "{item.message}"</span>
                    </React.Fragment>
                  }
                />
              </ListItem>
              <Divider variant="inset" component="li" />
            </div>
          ))}
        </List>
      </Box>
    );
  }
}
