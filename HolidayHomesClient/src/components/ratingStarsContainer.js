import React from 'react';
import StarRatingComponent from 'react-star-rating-component';
import fetchHelper from "../facades/fetchHelpers";
const URL = require("../../package.json").serverURL;

export default class Zvezdichka extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      rating: this.props.userRating,
      editableRating: false,
      componentUsedOnPath: 'expectToBeChangedDinamically',
      userItself: { username: "unauthorized" },
      rentableName: 'isNotAplaceName',
      placeName: 'isNotArentableName',
      placeORrentableName: 'willKnowInAwhile',
      userVoted: 'no'
    };
  }

  componentDidMount() {
    if (window.location.href.includes('places')) {
      this.setState({ componentUsedOnPath: 'api/places/addRating', placeName: this.props.pName, userItself: this.props.currentUser });
    } else if (window.location.href.includes('rentables')) {
      this.setState({ componentUsedOnPath: 'api/rentables/addRating', rentableName: this.props.rName, userItself: this.props.currentUser });
    }
  }

  onStarClick(nextValue, prevValue, name) {
    /*console.log('name: %s, nextValue: %s, prevValue: %s', name, nextValue, prevValue);*/
    this.setState({ rating: nextValue, userVoted: 'yes' });

    let submitNewRatingObject;
    if (this.state.rentableName !== 'isNotAplaceName') {
      submitNewRatingObject = {
        rentableName: this.state.rentableName,
        rating: nextValue,
        username: this.state.userItself.username
      }
    } else {
      submitNewRatingObject = {
        placeName: this.state.placeName,
        rating: nextValue,
        username: this.state.userItself.username
      }
    }

    const options = fetchHelper.makeOptions("POST", true, submitNewRatingObject);
    fetch(URL + this.state.componentUsedOnPath, options)
      .catch(err => {
        console.log(JSON.stringify(err));
      })
  }

  render() {
    return (
      <div className="col-md-4 reactiveStars">
        <h4>Your Rating (from current user)</h4>
        {/*{(this.state.userVoted !== 'yes') && (<p>(You haven't rated yet.)</p>)} */}
        <div style={{ fontSize: 30 }}>
          <StarRatingComponent
            name="cuzYouAreSkyFullOfStars"
            starCount={5}
            value={this.state.rating}
            onStarClick={this.onStarClick.bind(this)}
          />
        </div>
        <br />
      </div>
    );
  }
}