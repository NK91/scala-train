package org.train.utlis

import org.train.rx.extention.{Evaluation, ServiceException, SomeData}

/**
  * Created by kisilnazar on 22.06.16.
  */
trait EvaluationWithException extends Evaluation {
  override def someEvaluating(): SomeData = {
    throw ServiceException("Some evaluation exception")
  }
}

trait EvaluationWithIndexException extends Evaluation {
  override def someEvaluating(index: Int): SomeData = {
    throw ServiceException(s"Some indexed evaluation exception with index=$index")
  }
}

trait AllEvaluationException extends EvaluationWithException with EvaluationWithIndexException
