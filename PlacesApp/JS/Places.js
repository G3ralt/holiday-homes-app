import React from "react";
import { Text, View, FlatList, StyleSheet } from 'react-native';
import fetchHelper, {errorChecker} from "./fetchHelpers";
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

	getPlaces = (cb) => {
		this._errorMessage = "";
		this._messageFromServer = "";
		let resFromFirstPromise = null; //Pass on response the "second" promise so we can read errors from server
		const options = fetchHelper.makeOptions("POST", false);
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
	//	<ListItem
	//	roundAvatar
	//	title={item.placeName}
	//	subtitle={`${item.gpsLat} ${item.gpsLong}`}
	//	avatar={{uri : item.imgURL}}
	//	keyExtractor={item => item.placeName}
	//	/>
	<Text style={styles.item}>{item.placeName}, {item.gpsLat}, {item.gpsLong}, {item.description}, {item.rating}</Text>
	);
	
	render(){
		var rows = this.mapData(this.state.data);
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
	}
  })

export default Places;