import React, { Component } from 'react';
import GoogleMapReact from 'google-map-react';

const PlaceMarker = ({ text }) => {
  return (
    <div>
      <div className='placePin bounce'></div>
      <div className='textLabel'>{text}</div>
      <div className='placePulse'></div>
    </div>
  )
};

const RentableMarkers = ({ text }) => {
  return (
    <div key={text}>
      <div className='rentablePin bounce'></div>
      <div className='textLabel'>{text}</div>
      <div className='rentablePulse'></div>
    </div>
  )
};

let allRentableMarkers;

export default class Map extends Component {
  //   static defaultProps = {
  //     center: { lat: 40.7446790, lng: -73.9485420 },
  //     zoom: 11
  //   }
  // pGPSlat={place.gpsLat} pGPSlong={place.gpsLong}

  constructor(props) {
    super(props);
    this.state = {
      center: { lat: this.props.pGPSlat, lng: this.props.pGPSlong },
      zoom: 15
    }
    allRentableMarkers = this.props.allRentables.map(rentable => {
      return (
          <RentableMarkers key={rentable.rentableName} lat={rentable.gpsLat} lng={rentable.gpsLong} text={rentable.rentableName} />
      )
    });
  }
  render() {
    return (
      <div className='col-md-4 nopadding google-map'>
        <GoogleMapReact
          options={{ fullscreenControl: false }}
          bootstrapURLKeys={{ key: 'AIzaSyCBkhStVuUZ51OOLMY5YM_npWc_Lxr70Ro' }}
          defaultCenter={this.state.center}
          defaultZoom={this.state.zoom}>
          <PlaceMarker lat={this.state.center.lat} lng={this.state.center.lng} text={this.props.pName} />
          {allRentableMarkers}
        </GoogleMapReact>
      </div>
    )
  }
}