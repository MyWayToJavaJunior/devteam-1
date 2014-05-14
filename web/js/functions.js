var count = 1;

function addRequirement() {
    var Form = document.getElementById('order');
    var jobs = document.getElementById('jobs');
    var elem = jobs.cloneNode(true);
    Form.appendChild(elem);
    var job = document.getElementById('job'+count);
    var qualification = document.getElementById('qualification'+count);
    var spec = document.getElementById('count'+count);
    ++count;
    job.name = 'job'+count;
    job.id = 'job'+count;
    qualification.name = 'qualification'+count;
    qualification.id = 'qualification'+count;
    spec.name = 'count'+count;
    spec.id = 'count'+count;
    var jCount = document.getElementById('jobsCount');
    jCount.value = count;
}