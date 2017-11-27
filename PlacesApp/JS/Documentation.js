import React from "react";
import { Text, View } from 'react-native';

class Documentation extends React.Component {
	static navigationOptions = { title: "Read the Documentation" }
	render() {
	  return (
			<View><Text>Documentation</Text></View>
		)
	}
}

  export default Documentation;