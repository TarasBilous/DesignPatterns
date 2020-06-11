import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './instagram-user.reducer';
import { IInstagramUser } from 'app/shared/model/instagram-user.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IInstagramUserDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InstagramUserDetail = (props: IInstagramUserDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { instagramUserEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          InstagramUser [<b>{instagramUserEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="username">Username</span>
          </dt>
          <dd>{instagramUserEntity.username}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{instagramUserEntity.email}</dd>
          <dt>
            <span id="password">Password</span>
          </dt>
          <dd>{instagramUserEntity.password}</dd>
          <dt>
            <span id="age">Age</span>
          </dt>
          <dd>{instagramUserEntity.age}</dd>
          <dt>
            <span id="sex">Sex</span>
          </dt>
          <dd>{instagramUserEntity.sex}</dd>
        </dl>
        <Button tag={Link} to="/instagram-user" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/instagram-user/${instagramUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ instagramUser }: IRootState) => ({
  instagramUserEntity: instagramUser.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InstagramUserDetail);
