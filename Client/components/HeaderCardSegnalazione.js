import React from "react";

import CardHeader from "@material-ui/core/CardHeader";
import Avatar from "@material-ui/core/Avatar";
import IconButton from "@material-ui/core/IconButton";
import ShareIcon from "@material-ui/icons/Share";
import MoreVertIcon from "@material-ui/icons/MoreVert";
import Chip from "@material-ui/core/Chip";
import Menu from "@material-ui/core/Menu";
import MenuItem from "@material-ui/core/MenuItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import ListItemText from "@material-ui/core/ListItemText";
import ReportProblemIcon from "@material-ui/icons/ReportProblem";
import CloseIcon from "@material-ui/icons/Close";
import Skeleton from "@material-ui/lab/Skeleton";
import Rating from '@material-ui/lab/Rating';

const toInitials = (str) =>
  str
    .replace(/[^A-Z]/g, "")
    .concat(str.charAt(1).toUpperCase())
    .substring(0, 2);

export default class HeaderCardSegnalazione extends React.Component {
  constructor(props) {
    super(props);
    this.state = { anchorEl: null };
  }

  static defaultProps = {
    autore: "",
    userType: "",
    reputazione: "",
  };

  handleClick = (event) => {
    this.setState({ anchorEl: event.currentTarget });
  };

  handleClose = (event) => {
    this.setState({ anchorEl: null });
  };

  render() {
    return (
      <div>
        <CardHeader
          avatar={
            this.props.autore ? (
              <Avatar>{toInitials(this.props.autore)}</Avatar>
            ) : (
              <Skeleton variant="circle"><Avatar/></Skeleton>
            )
          }
          action={
            <IconButton
              aria-controls="simple-menu"
              aria-haspopup="true"
              onClick={this.handleClick}
            >
              <MoreVertIcon />
            </IconButton>
          }
          title={
            <span>
              <b>{this.props.autore ? this.props.autore : <Skeleton variant="text" width={150}/>}</b>
              {this.props.userType === "pro" ? (
                <Chip label="PRO" color="primary" size="small"></Chip>
              ) : (
                " "
              )}
            </span>
          }
          subheader={<span>{this.props.reputazione ? 
            <Rating defaultValue={this.props.reputazione} size="small" precision={0.1}/>
             : <Skeleton variant="text" width={100}/>}</span>}
        />
        <Menu
          id="menu"
          anchorEl={this.state.anchorEl}
          keepMounted
          open={Boolean(this.state.anchorEl)}
          onClose={this.handleClose}
        >
          {[
            { icon: <ShareIcon />, text: "Condividi" },
            { icon: <ReportProblemIcon />, text: "Segnala" },
            { icon: <CloseIcon />, text: "Chiudi" },
          ].map((item, index) => (
            <MenuItem onClick={this.handleClose}>
              <ListItemIcon>{item.icon}</ListItemIcon>
              <ListItemText primary={item.text} />
            </MenuItem>
          ))}
        </Menu>
      </div>
    );
  }
}
