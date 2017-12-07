import React from "react";
import { Text, View, Platform, TouchableOpacity, StyleSheet, Button, WebView, ScrollView, AsyncStorage } from 'react-native';
import { Constants, WebBrowser } from "expo";
import { StackNavigator } from 'react-navigation';
import Places from "./JS/Places";
import AddPlace from "./JS/AddPlace";
import Login from "./JS/Login";
import { readAsStringAsync } from "expo/src/FileSystem";


const Touchable = (props) => (
	<TouchableOpacity style={styles.button} onPress={props.onPress}>
    <Text style={styles.buttonText}>{props.title}</Text>
	</TouchableOpacity>)
	class HomeScreen extends React.Component {
		constructor() {
			super();
			this.state = {
				loggedIn: false, 
				username: "unauthorized",
			}
		}
		static navigationOptions = { title: 'PlacesApp - Group 8' };
		
		render() {
			const { navigate } = this.props.navigation;
			return (
				<ScrollView >
				<Text style={{ textAlign: "center", fontSize: 20 }}>Places App</Text>
				<Text style={{ textAlign: "center", fontSize: 16 }}>by Kasper, Anton, Andrian and Alexander</Text>
				<Touchable onPress={() => navigate("places")} title="Places" />
				<Touchable onPress={() => navigate("addPlace")} title="Add Place"/>
				<Touchable onPress={() => navigate("login")} title="Log in" />
				<Text style={{ textAlign: "center", fontSize: 16 }}>https://github.com/G3ralt/GROUP8-SP</Text>
				</ScrollView>
			)
		}
	}


	
	export default App = () => <RouteStack style={{ marginTop: Platform.OS === 'ios' ? 0 : Constants.statusBarHeight / 2 }} />
	
	const RouteStack = StackNavigator({
		Home: { screen: HomeScreen },
		places: { screen: Places },
		addPlace: { screen: AddPlace},
		login: {screen: Login}
	});

const styles = StyleSheet.create({
  button: {
    margin: 3,
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