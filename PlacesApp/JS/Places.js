import React from "react";
import { Text, View, FlatList, StyleSheet, Button, Image, ScrollView } from 'react-native';
import openMap from 'react-native-open-maps';
const URL = require("../package.json").serverURL;

class Places extends React.Component{
	static navigationOptions = {title:"All the places!"};


	constructor(props){
		super(props);
		this.state = {data: [], err:""};
	}

	componentWillMount(){
		let data = {
			method: 'POST',
			headers: {
				Accept: 'application/json',
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				username: "unauthorized"
			}),

		}
		fetch(URL + "api/places/all", data)
			.then(response => response.json())
			.then(responseJson => {
				console.log("RESPONSE JSON");
				this.setState({
					data: responseJson
				});
			}).catch(error => {
				console.log("error");
				console.error(error);
			});

		}

	  getPlaces = () => {
	  	let data = {
	  		method: 'POST',
	  		headers: {
	  			Accept: 'application/json',
	  			'Content-Type': 'application/json'
	  		},
	  		body: JSON.stringify({
	  			username: "unauthorized"
	  		}),

	  	}
	  	return fetch(URL + "api/places/all", data)
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
		const locations = this.state.data.map((item) =>{
			return (
				<View style={styles.item}>
				<Image
				style={{width:300, height:300}}
					source={{uri: item.imgURL}}
				/>
				<Text style={styles.inputHeader}>{item.placeName}</Text>
				<Text style={styles.inputHeader}>Latitude:{item.gpsLat}, Longitude:{item.gpsLong}</Text>
				<Text style={styles.description}>{item.description}</Text>
				<Text style={styles.rating}>Average rating: {item.rating}/5</Text>
				<Button style={styles.button} onPress={() =>
					openMap({latitude: item.gpsLat, longitude: item.gpsLong})} 
					title="See on Map"/>
				</View>
			)
		})
		
		return(
			<ScrollView style={styles.container}>
			{locations}
			</ScrollView>
		)
	}
}

const styles = StyleSheet.create({
	container: {
	 flex: 1,
	 paddingTop: 22
	},
	item: {
	  padding: 5,
	  fontSize: 18,
	  alignItems: "center",
	},
	description: {
		textAlign: "center",
		fontSize: 12,
	},
	rating: {
		textAlign:"center",
		color:"orange",
	},
	button: {
		margin: 3,
		alignItems: 'center',
		backgroundColor: '#ff650c'
	  },
	inputHeader: {
		textAlign: "center",
		fontSize: 18,
		margin: 5,
	},
  })

export default Places;