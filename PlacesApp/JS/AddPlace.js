import React from "react";
import { Text, View, FlatList, StyleSheet, TextInput, TouchableOpacity, Image } from 'react-native';
import MapView from 'react-native-maps';
const URL = require("../package.json").serverURL;

class AddPlace extends React.Component{
	static navigationOptions = {title:"Add a new place!"};

	constructor(){
		super();
		this.state = {data: "", err:"", placeName:"", gpsLat:0, gpsLong:0, placeDesc:"", placeImg:"https://loremflickr.com/300/300/city", placeUser:"user"};
	}


	componentDidMount(){
		navigator.geolocation.getCurrentPosition(
			(position) => {
		  		this.setState({
				gpsLat: position.coords.latitude,
				gpsLong: position.coords.longitude,
				err: null,
		  	});
		},(error) => this.setState({ err: error.message }),
		{ enableHighAccuracy: true, timeout: 20000, maximumAge: 1000 },
	  );
	  this.setState({
	  	placeImg: "https://loremflickr.com/300/300/city"
	  })
	  }

	  addPlace = () => {
	  	let data = {
	  		method: 'POST',
	  		headers: {
	  			Accept: 'application/json',
	  			'Content-Type': 'application/json'
	  		},
	  		body: JSON.stringify({
				placeName: this.state.placeName,
				description: this.state.placeDesc,
				imgURL: this.state.placeImg,
				gpsLat: this.state.gpsLat,
				gpsLong: this.state.gpsLong,
				user: {
					username: this.state.placeUser
				}
	  		}),

	  	}
	  	return fetch(URL + "api/places/create", data)
	  		.then(response => response.json())
	  		.then(responseJson => {
	  			console.log("RESPONSE JSON");
	  			return responseJson;
	  		})
	  		.catch(error => {
	  			console.log("error");
	  			console.error(error);
	  		});
	  }
	
	render(){
		return(
			<View style={styles.container}>
			<Image
			style={{width:300, height:300}}
				source={{uri: this.state.placeImg}}
			/>
			<Text style={styles.inputHeader}>Place Name:</Text>
			<TextInput
			style={styles.inputField}
			onChangeText={(placeName) => this.setState({placeName})}
			value={this.state.placeName}/>

			<Text style={styles.inputHeader}>Place Description</Text>
			<TextInput
			style={styles.inputField}
			onChangeText={(placeDesc) => this.setState({placeDesc})}
			value={this.state.placeDesc}/>
			<Text>Location:</Text>
			<Text>Latitude: {this.state.gpsLat}</Text>
			<Text>Longitude: {this.state.gpsLong}</Text>
			<Touchable onPress={this.addPlace()} title="Add Place"/>

			<MapView
			initialRegion={{
				latitude: this.state.gpsLat,
				longitude: this.state.gpsLong,
				latitudeDelta: 0.0922,
				longitudeDelta: 0.0421,
			}}/>
			</View>
		)
	}
}
const Touchable = (props) => (
	<TouchableOpacity style={styles.button} onPress={props.onPress}>
    <Text style={styles.buttonText}>{props.title}</Text>
	</TouchableOpacity>)

const styles = StyleSheet.create({
	container: {
		flex: 1,
		paddingTop: 22,
		alignItems: "center",
	},
	header: {
		padding: 10,
		fontSize: 20,
		height: 44,
	},
	inputHeader: {
		textAlign: "center",
		fontSize: 18,
		height: 30,
	},
	inputField: {
		alignItems: "center",
		height: 40,
		width: 300,
		borderColor: "black",
		borderWidth: 1,
		padding: 10,
	},
	button: {
		margin: 4,
		alignItems: 'center',
		backgroundColor: '#ff650c'
	},
	buttonText: {
		padding: 7,
		fontSize: 18,
		fontWeight: "bold",
		color: 'white'
	}
})

export default AddPlace;