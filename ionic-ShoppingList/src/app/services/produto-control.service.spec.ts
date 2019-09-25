import { TestBed } from '@angular/core/testing';

import { ProdutoControlService } from './produto-control.service';

describe('ProdutoControlService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ProdutoControlService = TestBed.get(ProdutoControlService);
    expect(service).toBeTruthy();
  });
});
