import React, { Component } from 'react'
import GoogleMapReact from 'google-map-react'
const AnyReactComponent = ({ text }) => <div>{ text }</div>;
export default class Map extends Component {
//   static defaultProps = {
//     center: { lat: 40.7446790, lng: -73.9485420 },
//     zoom: 11
//   }
  constructor(props){
      super(props);
      this.state = {
        center: { lat: 40.7446790, lng: -73.9485420 },
        zoom: 11
      }
  }
render() {
    return (
      <div className='col-md-4 google-map'>
        <GoogleMapReact
        bootstrapURLKeys={{key: 'AIzaSyCBkhStVuUZ51OOLMY5YM_npWc_Lxr70Ro'}}
          defaultCenter={ this.state.center }
          defaultZoom={ this.state.zoom }>
          <AnyReactComponent
            lat={ this.state.center.lat }
            lng={ this.state.center.lng }
            text={ 'Where\'s Alexandro?' }
          />
        </GoogleMapReact>
      </div>
    )
  }
}