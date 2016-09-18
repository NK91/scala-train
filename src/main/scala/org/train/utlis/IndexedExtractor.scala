package org.train.utlis

/**
  * Created by nk91 on 11.07.16.
  */
trait IndexedExtractor {

  def indexExtractor(id: Long): Int = 100 + id.toInt

}

trait IndexedExtractorWithError extends IndexedExtractor {
  override def indexExtractor(id: Long): Int = {
    throw new Exception(s"index extractor exception with $id")
  }
}
