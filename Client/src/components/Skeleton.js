import React from "react";

import Divider from "@material-ui/core/Divider";
import IconButton from "@material-ui/core/IconButton";

import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import ListItemText from "@material-ui/core/ListItemText";

import Drawer from "@material-ui/core/Drawer";
import ChevronLeftIcon from "@material-ui/icons/ChevronLeft";
import AppsIcon from "@material-ui/icons/Apps";
import SmsFailedIcon from "@material-ui/icons/SmsFailed";
import PersonIcon from "@material-ui/icons/Person";
import Avatar from "@material-ui/core/Avatar";

import AppBar from "@material-ui/core/AppBar";

import MenuIcon from "@material-ui/icons/Menu";
import Toolbar from "@material-ui/core/Toolbar";

import EmailIcon from "@material-ui/icons/Email";
import ExitToAppIcon from "@material-ui/icons/ExitToApp";

import Typography from "@material-ui/core/Typography";
import { Link, withRouter } from "react-router-dom";

import { withStyles } from "@material-ui/core/styles";

const toInitials = (str) =>
  str
    .replace(/[^A-Z]/g, "")
    .concat(str.charAt(1).toUpperCase())
    .substring(0, 2);

const useStyles = (theme) => ({
  root: {
    display: "flex",
  },
  drawerPaper: {
    width: 300,
  },
  drawerHeader: {
    display: "flex",
    alignItems: "center",
    padding: theme.spacing(0, 1),
    justifyContent: "flex-end",
  },
  toolbar: theme.mixins.toolbar,
  drawerTop: {
    paddingLeft: theme.spacing(3),
    lineHeight: 2,
    marginBottom: 25,
  },
  content: {
    flexGrow: 1,
  },
});

class Skeleton extends React.Component {
  constructor(props) {
    super(props);
    this.state = { mobileOpen: false, pageTitle: "Bacheca" };
    this.getTitoloPagina();
  }

  handleDrawerToggle = () => {
    this.setState({ mobileOpen: !this.state.mobileOpen });
  };

  handlePageChange = (title) => {
    this.handleDrawerToggle();
    this.setState({ pageTitle: title });
  };

  getTipoUtente = () => {
    return localStorage.getItem("tipoUtente")
      ? localStorage.getItem("tipoUtente")
      : 0;
  };

  getTitoloPagina = () => {
    console.log(this.getTipoUtente())
    menu[this.getTipoUtente()].forEach((item) => {
      console.log(this.props.location.pathname);
      if (item.url == this.props.location.pathname) {
        this.setState({pageTitle: item.text})
      }
    });
    return "";
  };

  render() {
    const { classes } = this.props;
    return (
      <div>
        <Drawer
          variant="temporary"
          anchor={"left"}
          open={this.state.mobileOpen}
          onClose={this.handleDrawerToggle}
          ModalProps={{
            keepMounted: true, // Better open performance on mobile.
          }}
          classes={{
            paper: classes.drawerPaper,
          }}
        >
          <div>
            <div className={classes.drawerHeader}>
              <IconButton onClick={this.handleDrawerToggle}>
                <ChevronLeftIcon />
              </IconButton>
            </div>
            <div className={classes.drawerTop}>
              <Avatar style={{ marginBottom: 20 }}>
                {toInitials(
                  localStorage.getItem("nome") +
                    " " +
                    localStorage.getItem("cognome")
                )}
              </Avatar>
              <Typography variant="h6" noWrap style={{ fontWeight: 600 }}>
                {localStorage.getItem("nome") +
                  " " +
                  localStorage.getItem("cognome")}
              </Typography>
              <span>{localStorage.getItem("email")}</span>
            </div>

            <Divider />
            <List>
              {menu[this.getTipoUtente()].map((item, index) =>
                item.text == "divider" ? (
                  <Divider key={index} />
                ) : (
                  <ListItem
                    button
                    component={Link}
                    key={index}
                    to={item.url}
                    onClick={() => this.handlePageChange(item.text)}
                  >
                    <ListItemIcon>{item.icon}</ListItemIcon>
                    <ListItemText primary={item.text} />
                  </ListItem>
                )
              )}
            </List>

            <div className={classes.toolbar} />
          </div>
        </Drawer>
        <AppBar position="fixed">
          <Toolbar>
            <IconButton
              color="inherit"
              aria-label="open drawer"
              edge="start"
              onClick={this.handleDrawerToggle}
            >
              <MenuIcon />
            </IconButton>
            <Typography variant="h6" noWrap>
              {this.state.pageTitle}
            </Typography>
          </Toolbar>
        </AppBar>
      </div>
    );
  }
}

const menu = [
  //Utente: 0
  [
    { text: "Bacheca", url: "/bacheca", icon: <AppsIcon /> },
    {
      text: "Le tue segnalazioni",
      url: "/mieSegnalazioni",
      icon: <SmsFailedIcon />,
    },
    {
      text: "Messaggi",
      url: "/listaMessaggi",
      icon: <EmailIcon />,
    },
    { text: "divider" },
    {
      text: "Profilo",
      url: "/profilo",
      icon: <PersonIcon />,
    },
    { text: "divider" },
    { text: "Esci", url: "/", icon: <ExitToAppIcon /> },
    {
      text: "Inserisci Metodo di Pagamento",
      url: "/metodoPagamento",
      icon: <EmailIcon />,
    },
    {
      text: "Lista Richieste",
      url: "/listaRichieste",
      icon: <EmailIcon />,
    },
  ],
  //Moderatore: 1
  [
    {
      text: "Segnalazioni da Approvare",
      url: "/segnalazioniDaApprovare",
      icon: <EmailIcon />,
    },
    { text: "Esci", url: "/", icon: <ExitToAppIcon /> },
  ],
  //Gestore: 2
  [
    {
      text: "Segnalazioni Approvate",
      url: "/segnalazioniApprovate",
      icon: <EmailIcon />,
    },
    { text: "Esci", url: "/", icon: <ExitToAppIcon /> }
  ],
  //Amministratore: 3
  [
    {
      text: "Homepage",
      url: "/homepageAdmin",
      icon: <EmailIcon />,
    },
    { text: "Esci", url: "/", icon: <ExitToAppIcon /> },
  ],
];

export default withRouter(withStyles(useStyles)(Skeleton));
