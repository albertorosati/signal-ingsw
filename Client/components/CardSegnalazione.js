import React from "react";

import HeaderCardSegnalazione from "../components/HeaderCardSegnalazione.js";
import Card from "@material-ui/core/Card";
import Skeleton from '@material-ui/lab/Skeleton';
import CardContent from "@material-ui/core/CardContent";
import CardActions from "@material-ui/core/CardActions";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import Moment from 'react-moment';
import { Link } from "react-router-dom";
import Image from "material-ui-image";
import StatoSegnalazione from "../components/StatoSegnalazione.js";


export default class CardSegnalazione extends React.Component {
  render() {
    return (
      <Card style={{ marginBottom: "20px" }}>
        <HeaderCardSegnalazione
          autore={this.props.autore}
          punti={this.props.punti}
          reputazione={this.props.reputazione}
        />
        {this.props.foto ? <Image src={this.props.foto} cover={true} aspectRatio={1.8} /> : <Skeleton variant="rect" height={300}/>}
        
        <CardContent>
          <Typography style={{ fontWeight: 600 }}>
            {this.props.titolo ? this.props.titolo : <Skeleton/>}
          </Typography>
          <Typography variant="body2" color="textSecondary" component="span">
          {this.props.pub_time ? <span><Moment fromNow ago>{this.props.pub_time}</Moment> fa</span> : <Skeleton width={80}/>}
          </Typography>
        </CardContent>

        {this.props.stato ? (
          <CardContent>
            <StatoSegnalazione stato={this.props.stato} />{" "}
          </CardContent>
        ) : null}

<CardActions disableSpacing>
        {this.props.id ? 
          <Button
            size="small"
            color="primary"
            component={Link}
            to={"/segnalazione/" + this.props.id}
          >
            Visualizza di più
          </Button>
          :
          <Skeleton width={100}/>
        
        }
        </CardActions>

        
      </Card>
    );
  }
}

CardSegnalazione.defaultProps = {
  userType: "normal",
};
