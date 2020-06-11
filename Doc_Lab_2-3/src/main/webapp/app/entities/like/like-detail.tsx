import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './like.reducer';
import { ILike } from 'app/shared/model/like.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILikeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const LikeDetail = (props: ILikeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { likeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Like [<b>{likeEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>Post</dt>
          <dd>{likeEntity.post ? likeEntity.post.id : ''}</dd>
          <dt>User</dt>
          <dd>{likeEntity.user ? likeEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/like" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/like/${likeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ like }: IRootState) => ({
  likeEntity: like.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(LikeDetail);
