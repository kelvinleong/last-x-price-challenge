import React, { Component } from 'react'

class ShakingError extends Component {
  constructor() { super(); this.state = { key: 0 }; }

  componentWillReceiveProps() {
    // update key to rerender the component to rerun the animation
    this.setState({ key: ++this.state.key });
  }
  
  render() {
    return <div key={this.state.key} className="bounce">{this.props.text}</div>;
  }
}

export default ShakingError
