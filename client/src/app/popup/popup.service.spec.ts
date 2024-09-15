import { TestBed } from '@angular/core/testing';

import { PopupService } from './popup.service';

describe('ReservePopupService', () => {
  let service: PopupService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PopupService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
