import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './follower-following.reducer';
import { IFollowerFollowing } from 'app/shared/model/follower-following.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFollowerFollowingDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FollowerFollowingDetail = (props: IFollowerFollowingDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { followerFollowingEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          FollowerFollowing [<b>{followerFollowingEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="canFollow">Can Follow</span>
          </dt>
          <dd>{followerFollowingEntity.canFollow ? 'true' : 'false'}</dd>
          <dt>Following</dt>
          <dd>{followerFollowingEntity.following ? followerFollowingEntity.following.id : ''}</dd>
          <dt>Followed By</dt>
          <dd>{followerFollowingEntity.followedBy ? followerFollowingEntity.followedBy.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/follower-following" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/follower-following/${followerFollowingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ followerFollowing }: IRootState) => ({
  followerFollowingEntity: followerFollowing.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FollowerFollowingDetail);
