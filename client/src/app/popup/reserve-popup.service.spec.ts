import { TestBed } from '@angular/core/testing';

import { ReservePopupService } from './reserve-popup.service';

describe('ReservePopupService', () => {
  let service: ReservePopupService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReservePopupService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
