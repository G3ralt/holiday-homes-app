import React from "react";
import { Text, View, FlatList, StyleSheet, Button } from 'react-native';
import fetchHelper, {errorChecker} from "./fetchHelpers";
import openMap from 'react-native-open-maps';
const URL = require("../package.json").serverURL;

class Places extends React.Component{
	static navigationOptions = {title:"All the places!"};


	constructor(){
		super();
		this.state = {data: "", err:""};
	}
	componentWillMount(){
		this.getPlaces((e,data)=>{
			if(e){
			  return this.setState({err:e.err});
			}
			this.setState({err:"",data});
		  });

	}

	_goToLocation(lat, long) {
		openMap({ latitude: lat, longitude: long });
	  }


	getPlaces = (cb) => {
		this._errorMessage = "";
		this._messageFromServer = "";
		let resFromFirstPromise = null; //Pass on response the "second" promise so we can read errors from server
		let user = {
			"username": "unauthorized"
		}
		const options = fetchHelper.makeOptions("POST", false, user);
		console.log(options);
		fetch(URL + "api/places/all", options)
			.then((res) => {
				resFromFirstPromise = res;
				console.log(res.json());
				return res.json();
			}).then((data) => {
				errorChecker(resFromFirstPromise, data);
				if (cb) {
					cb(null, data)
				}
			}).catch(err => {
				console.log(JSON.stringify(err))
				if (cb) {
					cb({
						err: err
					})
				}
			})
	}

	mapData = (a) => {
		if(a === ""){
			return "";
		}
		var rows = a.map(function(p){
			return p;
		})
		return rows;
	}

	_renderItem = ({item}) => (
	<View>
	<Image
	style={{width:300, height:300}}
		source={{uri: item.imgURL}}
	/>
	<Text style={styles.inputHeader}>{item.placeName}</Text>
	<Text style={styles.inputHeader}>{item.gpsLat}, {item.gpsLong}</Text>
	<Text style={styles.inputHeader}>{item.description}</Text>
	<Text style={styles.inputHeader}>{item.rating}</Text>
	<Button onPress={this._goToLocation()}>See on Maps</Button>
	</View>
	);
	
	render(){
		var rows = this.mapData(this.state.data);
		console.log("ROWS HERE!");
		console.log(rows);
		return(
			<View style={styles.container}>
			<FlatList
			data = {rows}
			renderItem={this._renderItem}
			/>
			</View>
		)
	}
}

const styles = StyleSheet.create({
	container: {
	 flex: 1,
	 paddingTop: 22
	},
	item: {
	  padding: 1,
	  fontSize: 18,
	  height: 44,
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
  })

export default Places;