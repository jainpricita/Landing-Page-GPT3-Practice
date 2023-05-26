import React from 'react'
import "./feature.css"
export default function Feature({title,text}) {
  return (
    <div className="gpt3_feature">
      <div className="gpt3_feature-title">
        <div></div>
        <h1>{title}</h1>
      </div>
      <div className="gpt3_feature-text">
        <p>{text}</p>
      </div>
    </div>
  )
}
