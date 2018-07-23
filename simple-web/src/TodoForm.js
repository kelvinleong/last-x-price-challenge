import React, { Component } from 'react'
import http from 'axios'
import ShakingError from './ShakingError'

window.apiUrl = 'http://localhost:8080'

class TodoFrom extends Component {
  constructor () {
    super()
    this.state = {
      number: 1,
      result: ''
    }
    this.changeInput = this.changeInput.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  changeInput (e) {
    this.setState({
      number: e.target.value
    })
  }

  handleSubmit (e) {
    e.preventDefault()
    const url = window.apiUrl + `/getAvgPrice?number=${this.state.number}`
    fetch(url)
      .then(res => res.json())
      .then(data => {
        if (data.code === -1) {
            this.setState({
              result: '',
              msg: "warning: " + data.msg,
              invalid: true
            })
         } else if (data.code === 0) {
            this.setState({ 
               result: data.result,
               invalid: false
            })
         }
      })
      .catch(e => console.log('error', e));
  }

  render () {
    const { msg, invalid } = this.state;
    return (
      <div>
        <form onSubmit={this.handleSubmit}>
          <label htmlFor="number">Number:</label>
          <input type="number"
                 id="number"
                 value={this.state.number}
                 onChange={this.changeInput}
                 min="1"
          />
          <label htmlFor="result">Result:</label>
          <input id="result"
                 type="number"
                 value={this.state.result}
                 disabled="disabled" 
                 readOnly/>
          <button>Submit</button>
        </form>
        <div className="res-block">
            {invalid && (
              <ShakingError text={msg}/>
            )}
        </div>
      </div>
    )
  }
}

export default TodoFrom
