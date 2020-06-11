import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './hashtag.reducer';
import { IHashtag } from 'app/shared/model/hashtag.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IHashtagDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const HashtagDetail = (props: IHashtagDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { hashtagEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Hashtag [<b>{hashtagEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{hashtagEntity.name}</dd>
          <dt>Post</dt>
          <dd>{hashtagEntity.post ? hashtagEntity.post.id : ''}</dd>
          <dt>Comment</dt>
          <dd>{hashtagEntity.comment ? hashtagEntity.comment.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/hashtag" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/hashtag/${hashtagEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ hashtag }: IRootState) => ({
  hashtagEntity: hashtag.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(HashtagDetail);
