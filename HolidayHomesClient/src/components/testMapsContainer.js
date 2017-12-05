import React, { Component } from 'react';
import GoogleMapReact from 'google-map-react';

const AnyReactComponent = () => {
  return (
    <div>
      <div className='pin bounce'></div>
      <div className='pulse'></div>
    </div>
  )
};

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
  }
  render() {
    return (
      <div className='col-md-4 google-map'>
        <GoogleMapReact
          bootstrapURLKeys={{ key: 'AIzaSyCBkhStVuUZ51OOLMY5YM_npWc_Lxr70Ro' }}
          defaultCenter={this.state.center}
          defaultZoom={this.state.zoom}>
          <AnyReactComponent
            lat={this.state.center.lat}
            lng={this.state.center.lng}
            key={this.props.pName}
          />
        </GoogleMapReact>
      </div>
    )
  }
}