import {Component, Input, OnInit} from '@angular/core';
import {Sprint} from '../../../../models/sprint.model';
import {SprintService} from '../../../../services/sprint.service';
import {ProjectService} from '../../../../services/project.service';
import {UsService} from '../../../../services/us.service';
import {Us} from '../../../../models/us.model';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';

@Component({
    selector: 'app-us-container',
    templateUrl: './sprint.component.html',
    styleUrls: ['./sprint.component.css']
})
export class SprintComponent implements OnInit {
    @Input() sprint: Sprint;
    @Input() connectedTo: string[];

    public userStories: Us[] = [];

    constructor(
        private usService: UsService,
        private projectService: ProjectService,
        private sprintService: SprintService
    ) {
    }

    ngOnInit(): void {
        this.sprintService.getUs(this.projectService.currentProject.getId(), this.sprint.getId()).subscribe(
            usList => {
                this.userStories = [];
                usList.forEach(us => this.userStories.push(Us.fromJSON(us)));
            }
        );
    }

    drop(event: CdkDragDrop<Us[], any>): void {
        if (event.previousContainer === event.container) {
            moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
        } else {
            transferArrayItem(event.previousContainer.data,
                event.container.data,
                event.previousIndex,
                event.currentIndex);
        }
    }
}
