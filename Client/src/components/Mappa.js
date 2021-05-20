import React, { Component } from "react";
import { MapContainer, TileLayer, Marker } from "react-leaflet";
import "../leaflet/leaflet.css";

import icon from "../leaflet/images/marker-icon-2x.png";
import iconShadow from "../leaflet/images/marker-shadow.png";

let DefaultIcon = L.icon({
  iconUrl: icon,
  shadowUrl: iconShadow,
  iconSize: new L.Point(50, 75),
});

L.Marker.prototype.options.icon = DefaultIcon;

export default class Mappa extends Component {
  render() {
    return (
      <MapContainer
        center={[this.props.lat, this.props.lon]}
        zoom={this.props.zoom}
        attributionControl={false}
        zoomControl={false}
      >
        <TileLayer
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
        />
        {this.props.markers
          ? this.props.markers.map((item, key) => <Marker key={key} position={item}></Marker>)
          : null}
      </MapContainer>
    );
  }
}
Mappa.defaultProps = {
  markers: [[44.49454, 11.3446]],
  lat: 44.49454,
  lon: 11.3446,
  zoom: 15,
};
