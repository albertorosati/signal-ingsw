import React from "react";

import Bacheca from "./pages/Bacheca";
import Profilo from "./pages/Profilo";
import MieSegnalazioni from "./pages/MieSegnalazioni";
import HomepageGS from "./pages/HomepageGS";
import SegnalazioniApprovate from "./pages/SegnalazioniApprovate";
import Chat from "./pages/Chat";
import HomepageAdmin from "./pages/HomepageAdmin";
import MetodoPagamento from "./pages/MetodoPagamento";
import ListaRichieste from "./pages/ListaRichieste";
import Segnalazione from "./pages/Segnalazione";
import NuovaSegnalazione from "./pages/NuovaSegnalazione";
import Registrazione from "./pages/Registrazione";
import ListaMessaggi from "./pages/ListaMessaggi";
import Login from "./pages/Login.js";
import SegnalazioniDaApprovare from "./pages/SegnalazioniDaApprovare";
import ScrollToTop from "./components/ScrollToTop";

import CssBaseline from "@material-ui/core/CssBaseline";

import Skeleton from "./components/Skeleton";

import { makeStyles } from "@material-ui/core/styles";

import { Switch, Route, BrowserRouter } from "react-router-dom";

import Moment from 'react-moment';
import 'moment/locale/it';


// Set the locale for every react-moment instance to French.
Moment.globalLocale = 'it';

const useStyles = makeStyles((theme) => ({
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
}));

function App(props) {
  const classes = useStyles();

  return (
    <div className={classes.root}>
      
      <main className={classes.content}>
      <CssBaseline />

      <BrowserRouter>
          <ScrollToTop>
            <Switch>
              <Route exact path="/" component={Login} />
              <Route exact path="/login" component={Login} />
              <Route exact path="/registrazione" component={Registrazione} />

              <div>
                <nav className={classes.drawer}>
                  <Skeleton />
                  <div className={classes.toolbar} />
                  <Route exact path="/bacheca" component={Bacheca} />
                  <Route
                    exact
                    path="/nuovaSegnalazione"
                    component={NuovaSegnalazione}
                  />
                  <Route
                    exact
                    path="/segnalazione/:id"
                    component={Segnalazione}
                  />
                  <Route
                    exact
                    path="/mieSegnalazioni"
                    component={MieSegnalazioni}
                  />
                  <Route exact path="/profilo" component={Profilo} />
                  <Route
                    exact
                    path="/listaMessaggi"
                    component={ListaMessaggi}
                  />
                  <Route exact path="/chat" component={Chat} />
                  <Route
                    exact
                    path="/metodoPagamento"
                    component={MetodoPagamento}
                  />

                  <Route
                    exact
                    path="/segnalazioniDaApprovare"
                    component={SegnalazioniDaApprovare}
                  />
                  
                  <Route
                    exact
                    path="/segnalazioniApprovate"
                    component={SegnalazioniApprovate}
                  />

                  <Route
                    exact
                    path="/listaRichieste"
                    component={ListaRichieste}
                  />

                  <Route
                    exact
                    path="/homepageAdmin"
                    component={HomepageAdmin}
                  />

                  <Route exact path="/homepageGS" component={HomepageGS} />
                </nav>
              </div>
            </Switch>
          </ScrollToTop>
      </BrowserRouter>
      </main>
    </div>
  );
}

export default App;
