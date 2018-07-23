import React, { Component } from 'react'
import http from 'axios'
import _ from 'lodash'
import TodoHeader from './TodoHeader'
import TodoForm from './TodoForm'


class App extends Component {
  constructor (props) {
    super(props)
    this.state = {
      data: []
    }
  }

  changeData (newData) {
    this.setState({
      data: newData
    })
  }

  render () {
    return (
      <div>
        <TodoHeader/>
        <TodoForm/>
      </div>
    )
  }
}

export default App
