import React from 'react'
import "./article.css"
export default function Article({imgUrl,date,text}) {
  return (
    <div className="gpt3_article">
      <div className="gpt3_article-image">
        <img src={imgUrl} alt="blog_image" />
      </div>
      <div className="gpt3_article-content">
        <div>
          <p>{date}</p>
          <h3>{text}</h3>
        </div>
        <p>Read Full Article</p>
      </div>
    </div>
  )
}
